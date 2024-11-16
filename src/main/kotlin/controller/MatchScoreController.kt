package controller

import constant.CurrentScoreKeys.KEY_PLAYER_1_GAMES
import constant.CurrentScoreKeys.KEY_PLAYER_1_POINTS
import constant.CurrentScoreKeys.KEY_PLAYER_1_SETS
import constant.CurrentScoreKeys.KEY_PLAYER_2_GAMES
import constant.CurrentScoreKeys.KEY_PLAYER_2_POINTS
import constant.CurrentScoreKeys.KEY_PLAYER_2_SETS
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import service.OngoingMatchesService
import service.PlayerService

private const val PATH_TO_JSP = "/match-score.jsp"

@WebServlet(urlPatterns = ["/match-score/*"])
class MatchScoreController : HttpServlet() {
    private val playerService = PlayerService()

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val uuid = req.pathInfo.trim('/')
        addRequestAttributes(uuid, req)
        req.getRequestDispatcher(PATH_TO_JSP).forward(req, resp)
        println(uuid)
        OngoingMatchesService.printData()
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val uuid = req.pathInfo.trim('/')
        val player = req.getParameter("player")
        // TODO move addScore(uuid, player) to service
        addRequestAttributes(uuid, req)
        req.getRequestDispatcher(PATH_TO_JSP).forward(req, resp)
    }

    private fun getNamesByUUID(uuid: String): List<String> {
        val match = OngoingMatchesService.getMatch(uuid)
        if (match!=null){
            val firstPlayerName = match.namePlayer1
            val secondPlayerName = match.namePlayer2
            return listOf(firstPlayerName, secondPlayerName)
        }
        return listOf()
    }

    private fun addRequestAttributes(uuid: String, req: HttpServletRequest) {
        val playersName = getNamesByUUID(uuid)
        req.setAttribute("firstPlayerName", playersName[0])
        req.setAttribute("secondPlayerName", playersName[1])
        req.setAttribute("uuid", uuid)
        val currentMatch = OngoingMatchesService.getMatch(uuid)
        if (currentMatch != null) {
            req.setAttribute(KEY_PLAYER_1_POINTS, currentMatch.pointsPlayer1)
            req.setAttribute(KEY_PLAYER_1_GAMES, currentMatch.gamesPlayer1)
            req.setAttribute(KEY_PLAYER_1_SETS, currentMatch.setsPlayer1)
            req.setAttribute(KEY_PLAYER_2_POINTS, currentMatch.pointsPlayer2)
            req.setAttribute(KEY_PLAYER_2_GAMES, currentMatch.gamesPlayer2)
            req.setAttribute(KEY_PLAYER_2_SETS, currentMatch.setsPlayer2)
        }
    }
}