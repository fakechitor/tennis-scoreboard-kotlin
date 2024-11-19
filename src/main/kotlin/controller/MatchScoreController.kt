package controller

import constant.CurrentScoreKeys.KEY_PLAYER_1_GAMES
import constant.CurrentScoreKeys.KEY_PLAYER_1_POINTS
import constant.CurrentScoreKeys.KEY_PLAYER_1_SETS
import constant.CurrentScoreKeys.KEY_PLAYER_2_GAMES
import constant.CurrentScoreKeys.KEY_PLAYER_2_POINTS
import constant.CurrentScoreKeys.KEY_PLAYER_2_SETS
import exception.GameFinishedException
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import service.FinishedMatchesPersistenceService
import service.MatchScoreCalculationService
import service.OngoingMatchesService

private const val PATH_TO_JSP = "/match-score.jsp"

@WebServlet(urlPatterns = ["/match-score/*"])
class MatchScoreController : HttpServlet() {
    private val calculationService = MatchScoreCalculationService()
    private val finishedMatches = FinishedMatchesPersistenceService()

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val uuid = req.pathInfo.trim('/')
        addRequestAttributes(uuid, req)
        req.getRequestDispatcher(PATH_TO_JSP).forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val uuid = req.pathInfo.trim('/')
        try {
            val player = req.getParameter("player")
            val matchScore = OngoingMatchesService.getMatch(uuid)
            calculationService.updateMatchState(matchScore, player)
            addRequestAttributes(uuid, req)
            req.getRequestDispatcher(PATH_TO_JSP).forward(req, resp)
        }
        catch (e : GameFinishedException){
            val match = OngoingMatchesService.getMatch(uuid).match
            finishedMatches.save(match)
            OngoingMatchesService.deleteMatch(uuid)
            req.getRequestDispatcher("/finished-match.jsp").forward(req, resp)
        }
        catch (e: IllegalArgumentException){

        }
    }

    private fun getNamesByUUID(uuid: String): List<String> {
        val match = OngoingMatchesService.getMatch(uuid)
        val firstPlayerName = match.namePlayer1
        val secondPlayerName = match.namePlayer2
        return listOf(firstPlayerName, secondPlayerName)
    }

    private fun addRequestAttributes(uuid: String, req: HttpServletRequest) {
        val playersName = getNamesByUUID(uuid)
        req.setAttribute("firstPlayerName", playersName[0])
        req.setAttribute("secondPlayerName", playersName[1])
        req.setAttribute("uuid", uuid)
        val currentMatch = OngoingMatchesService.getMatch(uuid)
        req.setAttribute(KEY_PLAYER_1_POINTS, currentMatch.statsPlayer1["point"])
        req.setAttribute(KEY_PLAYER_1_GAMES, currentMatch.statsPlayer1["game"])
        req.setAttribute(KEY_PLAYER_1_SETS, currentMatch.statsPlayer1["set"])
        req.setAttribute(KEY_PLAYER_2_POINTS, currentMatch.statsPlayer2["point"])
        req.setAttribute(KEY_PLAYER_2_GAMES, currentMatch.statsPlayer2["game"])
        req.setAttribute(KEY_PLAYER_2_SETS, currentMatch.statsPlayer2["set"])
    }
}