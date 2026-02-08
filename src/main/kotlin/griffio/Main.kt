package griffio

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.pgvector.PGvector
import griffio.migrations.Locations
import griffio.queries.Sample
import net.postgis.jdbc.geometry.Point
import net.postgis.jdbc.geometry.binary.BinaryParser
import net.postgis.jdbc.geometry.binary.BinaryWriter

import org.postgresql.ds.PGSimpleDataSource

private fun getSqlDriver(): SqlDriver {
    val datasource = PGSimpleDataSource()
    datasource.setURL("jdbc:postgresql://localhost:5432/geovectors")
    datasource.applicationName = "App Main"
    return datasource.asJdbcDriver()
}

val pointAdapter = object : ColumnAdapter<Point, String> {
    override fun encode(value: Point) = BinaryWriter().writeHexed(value)
    override fun decode(databaseValue: String) = BinaryParser().parse(databaseValue) as Point
}

val vectorAdapter = object: ColumnAdapter<PGvector, String> {
    override fun decode(databaseValue: String): PGvector = PGvector(databaseValue)
    override fun encode(value: PGvector): String = value.toString()
}

val adapters = Locations.Adapter(pointAdapter,vectorAdapter)

fun main() {

    val driver = getSqlDriver()

    Sample.Schema.create(driver)

    val q = Sample(driver, adapters).searchQueries

    println("Rows deleted: ${q.deleteAllLocations().value}")

    q.insertLocation(name = "Chamonix (FR)", longitude = 6.8694, latitude = 45.9237, embedding = "[0.689700, 0.083000, 0.719300]")
    q.insertLocation(name = "Zermatt (CH)", longitude = 7.7491, latitude = 46.0207, embedding = "[0.687800, 0.093600, 0.719900]")
    q.insertLocation(name = "St. Anton am Arlberg (AT)", longitude = 10.2640, latitude = 47.1286, embedding = "[0.670400, 0.121400, 0.732000]")
    q.insertLocation(name = "Whistler Blackcomb (CA)", longitude = -122.9574, latitude = 50.1163, embedding = "[-0.349700, -0.539700, 0.766000]")
    q.insertLocation(name = "Aspen Snowmass (US)", longitude = -106.8175, latitude = 39.1867, embedding = "[-0.224800, -0.741700, 0.632000]")
    q.insertLocation(name = "Niseko (JP)", longitude = 140.6874, latitude = 42.8048, embedding = "[-0.567000, 0.465000, 0.680000]")

    // Chamonix (FR)
    q.selectNearestByEmbeddingOnly("[0.689700, 0.083000, 0.719300]", 1).executeAsList()
        .forEach { println(it.name) }

    driver.close()

}
