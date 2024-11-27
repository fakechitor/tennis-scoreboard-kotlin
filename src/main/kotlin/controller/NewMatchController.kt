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

@WebServlet(name = "New match", value = ["/new-match"])
class NewMatchController : HttpServlet() {
    private val playerService = PlayerService
    private val pathToNewMatch = "/new-match.jsp"

    override fun doGet(
        req: HttpServletRequest,
        resp: HttpServletResponse,
    ) {
        req.getRequestDispatcher(pathToNewMatch).forward(req, resp)
    }

    override fun doPost(
        req: HttpServletRequest,
        resp: HttpServletResponse,
    ) {
        try {
            val name1 = req.getParameter("firstPlayer").trim()
            val name2 = req.getParameter("secondPlayer").trim()
            name1.validateDifference(name2)
            name1.validatePlayerName()
            name2.validatePlayerName()
            val player1 = playerService.findOrCreatePlayer(name1)
            val player2 = playerService.findOrCreatePlayer(name2)
            val uuid = UUID.randomUUID().toString()
            OngoingMatchesService.putMatch(
                uuid,
                MatchScoreModel(Match(player1.id, player2.id), player1.name, player2.name),
            )
            resp.sendRedirect("${req.contextPath}/match-score?uuid=$uuid")
        } catch (ex: RuntimeException) {
            req.apply {
                setAttribute("error", ex.message)
                setAttribute("errorMessage", ex.message)
                getRequestDispatcher(pathToNewMatch).forward(req, resp)
            }
        }
    }
}
