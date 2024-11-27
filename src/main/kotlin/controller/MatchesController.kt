package controller

import dto.MatchesRequestDto
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import service.FinishedMatchesPersistenceService
import util.Validation.Companion.validatePage
import util.Validation.Companion.validateSearchName

private const val PATH_TO_MATCHES = "/matches.jsp"
private const val OBJECTS_ON_PAGE = 5

@WebServlet(urlPatterns = ["/matches"])
class MatchesController : HttpServlet() {
    private val matchService = FinishedMatchesPersistenceService

    override fun doGet(
        req: HttpServletRequest,
        resp: HttpServletResponse,
    ) {
        val page = req.getParameter("page") ?: "1"
        val name = req.getParameter("filter_by_player_name") ?: ""
        name.trim()
        try {
            validateParameters(name, page)
            val pageNumber = page.toInt()
            val matchesRequest = MatchesRequestDto(pageNumber, name)
            val finishedMatches = matchService.getMatches(matchesRequest)
            val totalMatches = matchService.getMatchesAmount(name)
            val totalPages = (totalMatches + OBJECTS_ON_PAGE - 1) / OBJECTS_ON_PAGE
            req.setAttribute("matches", finishedMatches)
            req.setAttribute("additional", OBJECTS_ON_PAGE * (pageNumber - 1))
            req.setAttribute("page", pageNumber)
            req.setAttribute("size", OBJECTS_ON_PAGE)
            req.setAttribute("totalPages", totalPages)
            req.setAttribute("filterName", name)
            req.getRequestDispatcher(PATH_TO_MATCHES).forward(req, resp)
        } catch (e: IllegalArgumentException) {
            req.setAttribute("showError", true)
            req.setAttribute("errorMessage", e.localizedMessage)
            req.getRequestDispatcher(PATH_TO_MATCHES).forward(req, resp)
        } catch (e: RuntimeException) {
            req.setAttribute("showError", true)
            req.setAttribute("errorMessage", e.localizedMessage)
            req.getRequestDispatcher(PATH_TO_MATCHES).forward(req, resp)
        }
    }

    private fun validateParameters(
        name: String,
        page: String,
    ) {
        name.validateSearchName()
        page.validatePage()
    }
}
