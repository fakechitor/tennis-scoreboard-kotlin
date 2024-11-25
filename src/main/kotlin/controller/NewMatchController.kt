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


private const val PATH_TO_NEW_MATCH = "/new-match.jsp"
@WebServlet(name = "New match", value = ["/new-match"])
class NewMatchController : HttpServlet() {

    private val validator = Validation()
    private val playerService = PlayerService()


    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        req.getRequestDispatcher(PATH_TO_NEW_MATCH).forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        try {
            val firstPlayerName = req.getParameter("firstPlayer").trim()
            val secondPlayerName = req.getParameter("secondPlayer").trim()
            validator.validateNewMatchAttributes(firstPlayerName, secondPlayerName)
            val player1 = playerService.findOrCreatePlayer(firstPlayerName)
            val player2 = playerService.findOrCreatePlayer(secondPlayerName)
            val uuid = UUID.randomUUID().toString()
            val match = Match(player1.id, player2.id)
            OngoingMatchesService.putMatch(uuid, MatchScoreModel(match, player1.name, player2.name))
            resp.sendRedirect("${req.contextPath}/match-score?uuid=$uuid")
        } catch (e: IllegalArgumentException) {
            req.setAttribute("showError", true)
            req.setAttribute("errorMessage", e.localizedMessage)
            req.getRequestDispatcher(PATH_TO_NEW_MATCH).forward(req, resp)
        } catch (e: RuntimeException) {
            req.setAttribute("showError", true)
            req.setAttribute("errorMessage", e.localizedMessage)
            req.getRequestDispatcher(PATH_TO_NEW_MATCH).forward(req, resp)
        }
    }
}