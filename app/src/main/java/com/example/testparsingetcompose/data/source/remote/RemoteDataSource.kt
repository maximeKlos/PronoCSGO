package com.example.testparsingetcompose.data.source.remote

import android.util.Log
import com.example.testparsingetcompose.data.MyDataSource
import com.example.testparsingetcompose.data.model.Match
import com.example.testparsingetcompose.data.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.testparsingetcompose.data.tools.Result
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.jsoup.Jsoup

class RemoteDataSource(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MyDataSource {

    override suspend fun getUpcomingMatchesFromHLTV(eventID: Int): Result<List<Match>> =
        withContext(dispatcher) {
            return@withContext try {
                val list = mutableListOf<Match>()
                val doc = Jsoup.connect("https://www.hltv.org/matches?event=${eventID}").get()
                val matches = doc.getElementsByClass("upcomingMatch")
                matches.forEach { match ->
                    if (match.hasAttr("team1") && match.hasAttr("team2")) {
                        val format = match.getElementsByClass("matchMeta").text()
                        val analyticsLink = "https://www.hltv.org/${
                            match.getElementsByClass("matchAnalytics").attr("href")
                        }"

                        val matchData = Jsoup.connect(analyticsLink).get()
                            .getElementsByClass("analytics-header")

                        //Fetching match schedule
                        val timeUnix = matchData[0].getElementsByClass("event-time").select("span")
                            .attr("data-unix")

                        val matchId = match.select("a").attr("href").split("/")[2]
                        //Fetching team 1 data
                        val team1data = matchData[0].getElementsByClass("analytics-team-1")[0]
                        val team1LogoURL =
                            team1data.getElementsByClass("team-logo-container").select("img")
                                .attr("src")
                        val team1Name =
                            team1data.getElementsByClass("team-logo-container").select("img")
                                .attr("title")
                        val team1OddVictory =
                            (team1data.getElementsByClass("team-odds").select("a").text()
                                .substring(12).toFloat() * 10).toInt()

                        //Fetching team 2 data
                        val team2data = matchData[0].getElementsByClass("analytics-team-2")[0]
                        val team2LogoURL =
                            team2data.getElementsByClass("team-logo-container").select("img")
                                .attr("src")
                        val team2Name =
                            team2data.getElementsByClass("team-logo-container").select("img")
                                .attr("title")
                        val team2OddVictory =
                            (team2data.getElementsByClass("team-odds").select("a").text()
                                .substring(12).toFloat() * 10).toInt()

                        list.add(
                            Match(
                                matchId = matchId,
                                format = format,
                                pageLink = analyticsLink,
                                timeUnix = timeUnix,
                                team1LogoURL = team1LogoURL,
                                team1Name = team1Name,
                                team1OddVictory = team1OddVictory,
                                team2LogoURL = team2LogoURL,
                                team2Name = team2Name,
                                team2OddVictory = team2OddVictory
                            )
                        )
                    }
                }
                Result.Success(list)
            } catch (e: Exception) {
                Log.d("errorDataParsing", e.message.toString())
                Result.Error(e)
            }
        }

    override suspend fun getResults(eventID: Int): Result<List<Match>> = withContext(dispatcher) {
        return@withContext try {
            val list = mutableListOf<Match>()
            val doc = Jsoup.connect("https://www.hltv.org/results?event=${eventID}").get()
            val allResults = doc.getElementsByClass("result-con")
            allResults.forEach { result ->
                val timeUnix = result.attr("data-zonedgrouping-entry-unix")
                val matchId = result.select("a").attr("href").split("/")[2]
                val matchPage = "https://www.hltv.org/${result.select("a").attr("href")}"
                val format = result.getElementsByClass("map-text").text()

                val tableRow =
                    result.getElementsByClass("result").select("table").select("tbody").select("tr")
                        .select("td")

                val team1Name = tableRow[0].getElementsByClass("team-logo").attr("title")
                val team1LogoURL = tableRow[0].getElementsByClass("team-logo").attr("src")
                val team1Score = tableRow[1].selectFirst("span")?.text()!!.toInt()

                val team2Name = tableRow[2].getElementsByClass("team-logo").attr("title")
                val team2LogoURL = tableRow[2].getElementsByClass("team-logo").attr("src")
                val team2Score =
                    tableRow[1].selectFirst("span")?.nextElementSibling()?.text()!!.toInt()

                list.add(
                    Match(
                        matchId = matchId,
                        format = format,
                        pageLink = matchPage,
                        timeUnix = timeUnix,
                        team1LogoURL = team1LogoURL,
                        team1Name = team1Name,
                        team1Score = team1Score,
                        team2LogoURL = team2LogoURL,
                        team2Name = team2Name,
                        team2Score = team2Score
                    )
                )
            }

            Result.Success(list)
        } catch (e: Exception) {
            Log.d("errorDataParsing", e.message.toString())
            Result.Error(e)
        }
    }

    override suspend fun getUpcomingMatchesFromFirestore(eventID: Int): Result<List<Match>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(): Result<User?> = withContext(dispatcher) {
        return@withContext try {
            val db = Firebase.firestore
            var user: User? = null
            db.collection("users")
                .document(Firebase.auth.uid!!)
                .get()
                .addOnSuccessListener { result ->
                    user = User(
                        userID = result.id,
                        isAdmin = result.data?.get("isAdmin") as Boolean,
                        nickname = result.data?.get("nickname") as String
                    )
                }
            Result.Success(user)
        } catch (e: Exception) {
            Log.d("errorRetrievingUser", e.message.toString())
            Result.Error(e)
        }
    }
}