package controllers

import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}


class ApiController @Inject() (cc: ControllerComponents) extends AbstractController(cc){

  def index() = Action { implicit request: Request[AnyContent] =>

    Ok("HelloWorld")
  }

  def table(name:String, rowID: Int) = Action { implicit request: Request[AnyContent] =>

    Ok(Json.obj("table" -> name, "rowid" -> rowID))
  }



}
