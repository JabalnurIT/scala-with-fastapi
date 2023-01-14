import scala.language.postfixOps
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
//import akka.http.scaladsl.model.HttpMessage.AlreadyDiscardedEntity.future
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpRequest}
import akka.http.scaladsl.unmarshalling.Unmarshal
//import akka.stream.ActorMaterializer
import spray.json.DefaultJsonProtocol.{StringJsonFormat, mapFormat}
import spray.json.enrichAny

//import java.net.URLEncoder
import scala.concurrent.Await
import scala.concurrent.duration._

object Main {

  implicit val system: ActorSystem = ActorSystem()
//  implicit val materializer: ActorMaterializer = ActorMaterializer()
  import system.dispatcher

  private def fillMaskRequest(text:String): Unit = {
    val requestFillMask = HttpRequest(
      method = HttpMethods.POST,
      uri = "http://127.0.0.1:8000/fill_mask",
      entity = HttpEntity(
        ContentTypes.`application/json`,
        Map("text" -> text).toJson.toString()
      )
    )
    val futureRes = for {
      resp <- Http().singleRequest(requestFillMask)
      res <- Unmarshal(resp.entity).to[String]
    } yield res

    val res = Await.result(futureRes, 30.seconds)
    println(res)
  }

  private def perplexityRequest(text:String): Unit = {
    val requestPerplexity = HttpRequest(
      method = HttpMethods.POST,
      uri = "http://127.0.0.1:8000/perplexity",
      entity = HttpEntity(
        ContentTypes.`application/json`,
        Map("text" -> text).toJson.toString()
      )
    )
    val futureRes = for {
      resp <- Http().singleRequest(requestPerplexity)
      res <- Unmarshal(resp.entity).to[String]
    } yield res

    val res = Await.result(futureRes, 30.seconds)
    println(res)
  }

  def main(args: Array[String]): Unit = {
    val t1 = System.nanoTime()
    val inputFillMask = "1,Q42,[MASK] Adams,[MASK] writer and [MASK],[MASK],United Kingdom,Artist,1952,2001.0,natural causes,49.0"
    fillMaskRequest(inputFillMask)
    val duration1 = (System.nanoTime() - t1)/1e9d
    println(duration1)

    val t2 = System.nanoTime()
    val inputPerplexity = "1,Q42,Douglas Adams,English writer and humorist,Male,United Kingdom,Artist,1952,2001.0,natural causes,49.0"
    perplexityRequest(inputPerplexity)
    val duration2 = (System.nanoTime() - t2) / 1e9d
    println(duration2)
  }
}