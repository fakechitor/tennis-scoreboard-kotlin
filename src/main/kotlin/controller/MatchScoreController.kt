package controller

import constant.GameState
import exception.GameFinishedException
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import service.FinishedMatchesPersistenceService
import service.MatchScoreCalculationService
import service.OngoingMatchesService
import view.MatchScoreView

private const val PATH_TO_MATCH_SCORE = "/match-score.jsp"
private const val PATH_TO_FINISHED = "/finished-match.jsp"

@WebServlet(urlPatterns = ["/match-score/*"])
class MatchScoreController : HttpServlet() {
    private val calculationService = MatchScoreCalculationService()
    private val finishedMatches = FinishedMatchesPersistenceService()
    private val matchScoreView = MatchScoreView()


    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val uuid = req.pathInfo.trim('/')
        addRequestAttributes(uuid, req, GameState.NORMAL)
        req.getRequestDispatcher(PATH_TO_MATCH_SCORE).forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val uuid = req.pathInfo.trim('/')
        try {
            val player = req.getParameter("player")
            val matchScore = OngoingMatchesService.getMatch(uuid)
            calculationService.updateMatchState(matchScore, player)
            val state  = calculationService.getMatchState()
            addRequestAttributes(uuid, req, state)
            req.getRequestDispatcher(PATH_TO_MATCH_SCORE).forward(req, resp)
        }
        catch (e : GameFinishedException){
            val matchScore = OngoingMatchesService.getMatch(uuid)
            finishedMatches.save(matchScore.match)
            addRequestAttributes(uuid, req, GameState.FINISHED)
            OngoingMatchesService.deleteMatch(uuid)
            req.getRequestDispatcher(PATH_TO_FINISHED).forward(req, resp)
        }
        catch (e: IllegalArgumentException){
            TODO()
        }
    }



    private fun addRequestAttributes(uuid : String, req: HttpServletRequest, state : GameState) {
        req.setAttribute("uuid", uuid)
        val matchScore = OngoingMatchesService.getMatch(uuid)
        val playersNames = matchScoreView.getNamesList(matchScore)
        req.setAttribute("firstPlayer", playersNames[0])
        req.setAttribute("secondPlayer", playersNames[0])
        val scoreData = matchScoreView.getMatchScoreDataForView(matchScore, state)
        req.setAttribute("scoreData", scoreData)
        val columnNames = matchScoreView.getColumnNames(state)
        req.setAttribute("columnNames", columnNames)
    }
}