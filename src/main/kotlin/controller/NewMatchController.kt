@file:Suppress("ktlint:standard:no-wildcard-imports")

package controller

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import model.Match
import model.MatchScoreModel
import service.OngoingMatchesService
import service.PlayerService
import util.Validation.Companion.validateDifference
import util.Validation.Companion.validatePlayerName
import java.util.*

private const val PATH_TO_NEW_MATCH = "/new-match.jsp"

@WebServlet(name = "New match", value = ["/new-match"])
class NewMatchController : HttpServlet() {
    private val playerService = PlayerService

    override fun doGet(
        req: HttpServletRequest,
        resp: HttpServletResponse,
    ) {
        req.getRequestDispatcher(PATH_TO_NEW_MATCH).forward(req, resp)
    }

    override fun doPost(
        req: HttpServletRequest,
        resp: HttpServletResponse,
    ) {
        try {
            val firstPlayerName = req.getParameter("firstPlayer").trim()
            val secondPlayerName = req.getParameter("secondPlayer").trim()
            validatePlayers(firstPlayerName, secondPlayerName)
            val player1 = playerService.findOrCreatePlayer(firstPlayerName)
            val player2 = playerService.findOrCreatePlayer(secondPlayerName)
            val uuid = UUID.randomUUID().toString()
            val match = Match(player1.id, player2.id)
            OngoingMatchesService.putMatch(uuid, MatchScoreModel(match, player1.name, player2.name))
            resp.sendRedirect("${req.contextPath}/match-score?uuid=$uuid")
        } catch (ex: Exception) {
            when (ex) {
                is IllegalArgumentException, is RuntimeException -> {
                    req.setAttribute("showError", true)
                    req.setAttribute("errorMessage", ex.localizedMessage)
                    req.getRequestDispatcher(PATH_TO_NEW_MATCH).forward(req, resp)
                }
            }
        }
    }

    private fun validatePlayers(
        name1: String,
        name2: String,
    ) {
        name1.validateDifference(name2)
        name1.validatePlayerName()
        name2.validatePlayerName()
    }
}
