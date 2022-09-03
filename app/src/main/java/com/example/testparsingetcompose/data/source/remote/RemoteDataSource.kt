package com.example.testparsingetcompose.data.source.remote

import android.util.Log
import com.example.testparsingetcompose.data.MatchesDataSource
import com.example.testparsingetcompose.data.model.Match
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.testparsingetcompose.data.tools.Result
import org.jsoup.Jsoup

class RemoteDataSource(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): MatchesDataSource {

    override suspend fun getMatches(eventID: Int): Result<List<Match>> = withContext(dispatcher) {
        return@withContext try {
            val list = mutableListOf<Match>()
            val doc = Jsoup.connect("https://www.hltv.org/matches?event=${eventID}").get()
            val match = doc.getElementsByClass("matchAnalytics")
            for (i in match.indices) {
                //Récupération du format du match et du lien vers la page d'analyse
                val format = doc.getElementsByClass("matchMeta")[i].text()
                val analyticsLink = "https://www.hltv.org/" + match[i].attr("href")

                //Récupération des données du match
                val matchData = Jsoup.connect(analyticsLink).get().getElementsByClass("analytics-header")

                //Récupération de l'heure du match
                val timeUnix = matchData[0].getElementsByClass("event-time").select("span").attr("data-unix")

                //Récupération des données de l'équipe 1
                val team1data = matchData[0].getElementsByClass("analytics-team-1")[0]
                val team1LogoURL = team1data.getElementsByClass("team-logo-container").select("img").attr("src")
                val team1Name = team1data.getElementsByClass("team-logo-container").select("img").attr("title")
                val team1OddVictory = (team1data.getElementsByClass("team-odds").select("a").text().substring(12) .toFloat() * 10).toInt()

                //Récupération des données de l'équipe 2
                val team2data = matchData[0].getElementsByClass("analytics-team-2")[0]
                val team2LogoURL = team2data.getElementsByClass("team-logo-container").select("img").attr("src")
                val team2Name = team2data.getElementsByClass("team-logo-container").select("img").attr("title")
                val team2OddVictory = (team2data.getElementsByClass("team-odds").select("a").text().substring(12) .toFloat() * 10).toInt()

                list.add(
                    Match(
                        format = format,
                        analyticsLink = analyticsLink,
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

            Result.Success(list)
        } catch (e: Exception){
            Log.d("errorDataParsing", e.message.toString())
            Result.Error(e)
        }
    }

}