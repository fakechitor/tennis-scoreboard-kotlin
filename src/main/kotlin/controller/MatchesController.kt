package controller

import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import service.FinishedMatchesPersistenceService

private const val PATH_TO_JSP = "/matches.jsp"

@WebServlet(urlPatterns = ["/matches"])
class MatchesController : HttpServlet() {
    private val finishedMatches = FinishedMatchesPersistenceService()

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        req.setAttribute("matches",finishedMatches.getAll())
        req.getRequestDispatcher(PATH_TO_JSP).forward(req, resp)
    }
}