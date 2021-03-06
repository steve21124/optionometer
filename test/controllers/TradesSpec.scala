package controllers

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
import com.github.nscala_time.time.Imports._
import Trades.TradeParams

@RunWith(classOf[JUnitRunner])
class TradesSpec extends Specification {
  
  "Trades" should {
    
    "calculate min & max days" in {
      val now = DateTime.now
      if (now.getMonthOfYear > 1) {
        Trades.minMaxDays(now.getYear, now.getMonthOfYear-1) must equalTo((None, None))
      }
      Trades.minMaxDays(now.getYear-1, now.getMonthOfYear) must equalTo((None, None))
      Trades.minMaxDays(now.getYear-1, now.getMonthOfYear-1) must equalTo((None, None))
      val minMax = Trades.minMaxDays(now.getYear, now.getMonthOfYear) 
      minMax must not equalTo((None, None))
      minMax._1 must beSome
      minMax._2 must beSome
    }
    
    "return a two leg trade" in {
      running(FakeApplication()) {
	      val now = DateTime.now
	      //TODO get und & legs from DB
	      val params = new TradeParams("MSFT", now.getYear.toString, now.getMonthOfYear.toString, "L34.00P-S35.00P")
	      Trades.twoLegTrade(params) must beSome
      }
    }
    
    "return a four leg trade" in {
      running(FakeApplication()) {
	      val now = DateTime.now
	      //TODO get und & legs from DB
	      val params = new TradeParams("MSFT", now.getYear.toString, now.getMonthOfYear.toString, "L35.00C-S36.00C-S36.00C-L37.00C")
	      Trades.fourLegTrade(params) must beSome
      }
    }
    
  }

}