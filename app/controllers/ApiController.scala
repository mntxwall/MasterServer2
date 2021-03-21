package controllers

import javax.inject.Inject
import models.MasterRepository
import play.api.libs.json.{JsValue, Json}
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


  def rollBox() = Action{
    implicit request: Request[AnyContent] =>

      println(BoxForm.bindFromRequest().get)

      Ok(Json.obj("result" -> 0))

  }

  def setCookies() = Action { implicit request =>


    println("SetCookies")

    println(request.session.get("name"))

    request.session.get("name") match {
      case Some("Hello") =>
        println("Hello")
        Ok(Json.obj("result" -> 1))
      case _ =>
        println("NotHello")
        Ok(Json.obj("result" -> 0)).withSession("name" -> "Hello")
    }

  }

  def clearCookies() = Action {
    implicit request =>
      println("clearCookie")
      Ok(Json.obj("result" -> 2)).withNewSession
  }


  def upload() = Action(parse.multipartFormData){ implicit request =>

    /*
    * upload the file to /tmp directory
    * rename file name to miniseconds
    * */
    request.body.file("file").map{ x =>

      println(x.filename)

      val json:JsValue = Json.obj("file" -> s"Hello", "upresult" -> 0)

      //Ok(Json.parse(s"""{"upresult":0 }"""))
      Ok(json)

    }.getOrElse(Ok(Json.parse(s"""{"upresult":1 }""")))

  }





}
