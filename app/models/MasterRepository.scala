package models

import anorm.{Macro, RowParser, SQL, ~}
import anorm.SqlParser.get
import javax.inject.Inject
import play.api.db.DBApi
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class MasterDB(id: Int, name:String, suspect: String, contact: String,
                    contact_num_reg_place: String, durationtime_count_all: Int,
                    cnt_count_all: Int, contact_location: String, user_name: String,
                    certification_code: String, mealtype: String, capture_time_read:String,
                    dt:String
                   )
class MasterRepository @Inject()(dbApi: DBApi) {

  private val db = dbApi.database("default")

  implicit val locationWrites: Writes[MasterDB] = Json.writes[MasterDB]



  def getAllRows() = {


    val parser: RowParser[MasterDB] = Macro.namedParser[MasterDB]

    Json.toJson(db.withConnection{ implicit c =>
      SQL(s"SELECT * from master").as(parser.*)
    })


  }

}
