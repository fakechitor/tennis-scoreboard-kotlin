package controller

import constant.GameState
import exception.GameFinishedException
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import service.CalculationService
import service.FinishedMatchesPersistenceService
import service.OngoingMatchesService
import view.MatchScoreView

@WebServlet(urlPatterns = ["/match-score/*"])
class MatchScoreController : HttpServlet() {
    private val calculationService = CalculationService
    private val finishedMatches = FinishedMatchesPersistenceService
    private val matchScoreView = MatchScoreView()
    private val pathToFinished = "/finished-match.jsp"
    private val pathToMatchScore = "/match-score.jsp"

    override fun doGet(
        req: HttpServletRequest,
        resp: HttpServletResponse,
    ) {
        val uuid = req.getParameter("uuid")
        addRequestAttributes(req, uuid, GameState.NORMAL)
        req.getRequestDispatcher(pathToMatchScore).forward(req, resp)
    }

    override fun doPost(
        req: HttpServletRequest,
        resp: HttpServletResponse,
    ) {
        val uuid = req.getParameter("uuid")
        try {
            val player = req.getParameter("player")
            val matchScore = OngoingMatchesService.getMatch(uuid)
            calculationService.updateMatchState(matchScore, player)
            addRequestAttributes(req, uuid, calculationService.getMatchState())
            req.getRequestDispatcher(pathToMatchScore).forward(req, resp)
        } catch (ex: GameFinishedException) {
            val matchScore = OngoingMatchesService.getMatch(uuid)
            finishedMatches.save(matchScore.match)
            addRequestAttributes(req, uuid, GameState.FINISHED)
            OngoingMatchesService.deleteMatch(uuid)
            req.getRequestDispatcher(pathToFinished).forward(req, resp)
        } catch (ex: RuntimeException) {
            req.apply {
                setAttribute("showError", true)
                setAttribute("errorMessage", ex.localizedMessage)
                getRequestDispatcher(pathToMatchScore).forward(req, resp)
            }
        }
    }

    private fun addRequestAttributes(
        request: HttpServletRequest,
        matchUuid: String,
        gameState: GameState,
    ) = request.apply {
        val matchScore = OngoingMatchesService.getMatch(matchUuid)
        val scoreData = matchScoreView.getMatchScoreDataForView(matchScore, gameState)
        val columnNames = matchScoreView.getColumnNames(gameState)

        setAttribute("uuid", matchUuid)
        setAttribute("firstPlayer", matchScore.match.player1?.name)
        setAttribute("secondPlayer", matchScore.match.player2?.name)
        setAttribute("scoreData", scoreData)
        setAttribute("columnNames", columnNames)
    }
}
