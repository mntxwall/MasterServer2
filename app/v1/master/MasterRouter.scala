package v1.master

import controllers.ApiController
import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter

import play.api.routing.sird._

class MasterRouter @Inject()(controller:ApiController) extends SimpleRouter{
  override def routes: Routes = {
    case GET(p"/") =>
      controller.index
    case DELETE(p"/first" ? q_o"table=$table" & q_o"rowID=${int(rowID)}") =>
      controller.table(table.getOrElse(""), rowID.getOrElse(0))
    case GET(p"/first") =>
      controller.getMasterRows()
    case POST(p"/box") =>
      controller.rollBox()
    case POST(p"/cookies") =>
      controller.cookiesTest()
    case POST(p"/upload") =>
      controller.upload();
  }
}
