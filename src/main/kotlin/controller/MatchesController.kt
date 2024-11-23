package controller

import dto.MatchesRequestDto
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
        val page = req.getParameter("page").toInt()
        val name = req.getParameter("filter_by_player_name") ?: null
        //TODO make validation for page > 0
        val matchesRequest = MatchesRequestDto(page , name)
        val finishedMatches = finishedMatches.getMatches(matchesRequest)
        req.setAttribute("matches", finishedMatches)
        req.setAttribute("additional", 5 * (page-1))
        req.setAttribute("page" ,page.toString())
        req.setAttribute("size", 5)
        req.setAttribute("totalPages", 2)
        req.getRequestDispatcher(PATH_TO_JSP).forward(req, resp)
    }
}