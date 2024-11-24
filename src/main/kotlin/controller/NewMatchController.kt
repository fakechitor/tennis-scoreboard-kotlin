package controller

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import model.Match
import model.MatchScoreModel
import service.OngoingMatchesService
import service.PlayerService
import util.Validation
import java.util.*

@WebServlet(name = "New match", value = ["/new-match"])
class NewMatchController : HttpServlet() {

    private val validator = Validation()
    private val playerService = PlayerService()


    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        req.getRequestDispatcher("/new-match.jsp").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        try {
            val firstPlayerName = req.getParameter("firstPlayer")
            val secondPlayerName = req.getParameter("secondPlayer")
            validator.validateNewMatchAttributes(firstPlayerName)
            validator.validateNewMatchAttributes(secondPlayerName)
            val player1 = playerService.findOrCreatePlayer(firstPlayerName)
            val player2 = playerService.findOrCreatePlayer(secondPlayerName)
            val uuid = UUID.randomUUID().toString()
            val match = Match(player1.id, player2.id)
            OngoingMatchesService.putMatch(uuid, MatchScoreModel(match, player1.name, player2.name))
            resp.sendRedirect("${req.contextPath}/match-score?uuid=$uuid")
        } catch (e: IllegalArgumentException) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.message)
        }
    }
}