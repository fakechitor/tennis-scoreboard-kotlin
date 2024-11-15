package controller

import dto.PlayerDto
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import model.Player
import service.PlayerService
import util.Validation

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
        createPlayerIfNotExists(firstPlayerName)
        createPlayerIfNotExists(secondPlayerName)
        resp.sendRedirect("${req.contextPath}/match-score")
    }

    private fun createPlayerIfNotExists(name: String): Player {
        if (playerService.getByName(name) == null) {
            return playerService.save(PlayerDto(name))
        }
        return playerService.getByName(name) as Player
    }
}