package controller

import dto.MatchesRequestDto
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import service.FinishedMatchesPersistenceService
import util.Validation

private const val PATH_TO_JSP = "/matches.jsp"

@WebServlet(urlPatterns = ["/matches"])
class MatchesController : HttpServlet() {
    private val finishedMatches = FinishedMatchesPersistenceService()
    private val validation = Validation()

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val page = req.getParameter("page")
        val name = req.getParameter("filter_by_player_name")
        try {
            validation.validateMatchesAttributes(page, name)
            val pageNumber = page.toInt()
            val matchesRequest = MatchesRequestDto(pageNumber, name)
            val finishedMatches = finishedMatches.getMatches(matchesRequest)
            // TODO make setAttribute more beautiful)
            req.setAttribute("matches", finishedMatches)
            req.setAttribute("additional", 5 * (pageNumber - 1))
            req.setAttribute("page", page)
            req.setAttribute("size", 5)
            req.setAttribute("totalPages", 2)
            req.setAttribute("filterName", name)
            req.getRequestDispatcher(PATH_TO_JSP).forward(req, resp)
        } catch (e: IllegalArgumentException) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.message)
        }
    }
}