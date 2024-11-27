package controller

import dto.MatchesRequestDto
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import service.FinishedMatchesPersistenceService
import util.Validation.Companion.checkIsPositive
import util.Validation.Companion.throwIfLonger
import util.Validation.Companion.toIntOrThrow

@WebServlet(urlPatterns = ["/matches"])
class MatchesController : HttpServlet() {
    private val matchService = FinishedMatchesPersistenceService
    private val pathToMatches = "/matches.jsp"
    private val objectsOnPage = 5
    private val maxNameLength = 20

    override fun doGet(
        req: HttpServletRequest,
        resp: HttpServletResponse,
    ) {
        val pageNumber = req.getParameter("page")?.toIntOrThrow() ?: 1
        val name = (req.getParameter("filter_by_player_name") ?: "").trim()
        try {
            name.throwIfLonger(maxNameLength)
            pageNumber.checkIsPositive()
            val totalPages = (matchService.getMatchesAmount(name) + objectsOnPage - 1) / objectsOnPage
            req.apply {
                setAttribute("matches", matchService.getMatches(MatchesRequestDto(pageNumber, name)))
                setAttribute("additional", objectsOnPage * (pageNumber - 1))
                setAttribute("page", pageNumber)
                setAttribute("size", objectsOnPage)
                setAttribute("totalPages", totalPages)
                setAttribute("filterName", name)
                getRequestDispatcher(pathToMatches).forward(req, resp)
            }
        } catch (e: RuntimeException) {
            req.apply {
                setAttribute("showError", true)
                setAttribute("errorMessage", e.localizedMessage)
                getRequestDispatcher(pathToMatches).forward(req, resp)
            }
        }
    }
}
