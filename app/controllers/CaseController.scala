package controllers

import play.api.libs.json.Json
import play.api.mvc.{AnyContent, BaseController, ControllerComponents, Request}

import javax.inject.Inject

class CaseController  @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok("")
  }

  def doMatching() = Action {
    implicit request: Request[AnyContent] =>

      println(request.body)
      Ok(Json.obj("result" -> 0))

  }
}
