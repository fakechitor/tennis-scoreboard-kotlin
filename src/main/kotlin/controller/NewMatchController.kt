package controller

import dto.PlayerDto
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import model.Match
import model.MatchScoreModel
import model.Player
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
        val firstPlayerName = req.getParameter("firstPlayer")
        val secondPlayerName = req.getParameter("secondPlayer")
        val player1 = findOrCreatePlayer(firstPlayerName)
        val player2 = findOrCreatePlayer(secondPlayerName)
        val uuid = UUID.randomUUID().toString()
        val match = Match(player1.id,player2.id)
        OngoingMatchesService.putMatch(uuid, MatchScoreModel(match, player1.name, player2.name))
        resp.sendRedirect("${req.contextPath}/match-score/$uuid")
    }

    private fun findOrCreatePlayer(name: String): Player {
        if (playerService.getByName(name) == null) {
            return playerService.save(PlayerDto(name))
        }
        return playerService.getByName(name) as Player
    }
}