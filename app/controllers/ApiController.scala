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


  def rollBox() = Action{
    implicit request: Request[AnyContent] =>

      println(BoxForm.bindFromRequest().get)

      Ok(Json.obj("result" -> 0))

  }





}
