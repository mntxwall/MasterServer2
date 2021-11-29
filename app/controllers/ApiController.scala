package controllers

import javax.inject.Inject
import models.MasterRepository
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import play.api.data.Forms._
import play.api.data._


case class Boxes(relationship: Option[Boolean], trajectory: Option[Boolean], others: Option[Boolean],
                 useChoice:Option[Boolean], inputValue: Option[String])

class ApiController @Inject() (cc: ControllerComponents,
                               masterRepository:MasterRepository) extends AbstractController(cc){

  val BoxForm = Form(
    mapping(
      "relationship" -> optional(boolean),
      "trajectory" -> optional(boolean),
      "others" -> optional(boolean),
      "useChoice" -> optional(boolean),
      "inputValue" -> optional(text)
    )(Boxes.apply)(Boxes.unapply)
  )

  def index() = Action { implicit request: Request[AnyContent] =>

    Ok("HelloWorld")
  }

  def table(name:String, rowID: Int) = Action { implicit request: Request[AnyContent] =>

    Ok(Json.obj("table" -> name, "rowid" -> rowID))
  }

  def getMasterRows() = Action{
    implicit request: Request[AnyContent] =>
      Ok(masterRepository.getAllRows())

  }

  def Hello() = Action(parse.multipartFormData) {
    implicit request =>
      //val t = request.body.asMultipartFormData

      Ok("Hello")
  }

  def upload() = Action(parse.multipartFormData){ implicit request =>


    request.body.file("file").map{ x =>

      println(x.filename)
      //val filename = LocalDateTime.now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))
      val filename = s"${LocalDateTime.now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))}.${x.filename.split("\\.")(1)}"
      //val filename = x.filename
      val fileWithPath:String = s"/tmp/fileUploads/$filename"
      //val fileWithPath:String = s"/tmp/fileUploads/$filename"

      x.ref.atomicMoveWithFallback(Paths.get(fileWithPath))

      Files.setPosixFilePermissions(Paths.get(fileWithPath),
        PosixFilePermissions.fromString("rw-r--r--"))

      val json:JsValue = Json.obj("file" -> s"$filename", "upresult" -> 0)

      //Ok(Json.parse(s"""{"upresult":0 }"""))
      Ok(json)

    }.getOrElse(Ok(Json.parse(s"""{"upresult":1 }""")))


  }


  def rollBox() = Action{
    implicit request: Request[AnyContent] =>

      println(BoxForm.bindFromRequest().get)

      Ok(Json.obj("result" -> 0))

  }

  def cookiesTest() = Action{
    implicit request =>
      request.session.get("username") match {
        case Some(value) =>
          println("got cookies value")
          Ok(Json.obj("result" -> 1))
        case _ =>
          println("cookies value empty")
          Ok(Json.obj("result" -> 0)).withSession("username" -> "cw")
            .withHeaders("Access-Control-Allow-Credentials" ->"true")
      }
  }


}
