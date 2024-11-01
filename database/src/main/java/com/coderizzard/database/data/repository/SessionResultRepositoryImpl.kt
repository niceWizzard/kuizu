package com.coderizzard.database.data.repository

import com.coderizzard.core.ResultState
import com.coderizzard.core.data.model.session.SessionResult
import com.coderizzard.core.data.model.session.SessionResultWithUserAnswers
import com.coderizzard.database.data.database.dao.SessionAnswerDao
import com.coderizzard.database.data.database.dao.SessionDao
import com.coderizzard.database.data.database.dao.SessionResultDao
import com.coderizzard.database.data.database.model.session.toEntity
import com.coderizzard.database.domain.repository.QuizRepository
import com.coderizzard.database.domain.repository.SessionResultRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SessionResultRepositoryImpl @Inject constructor(
    private val sessionResultDao: SessionResultDao,
    private val sessionDao: SessionDao,
    private val quizRepository: QuizRepository,
    private val sessionAnswerDao: SessionAnswerDao,
) : SessionResultRepository {
    override suspend fun getLatestResult(quizId: String): ResultState<SessionResultWithUserAnswers> = withContext(Dispatchers.IO){
        try {
            val result = sessionResultDao.getLatestResult(quizId).toSessionResult()
            val quiz = quizRepository.getById(quizId)
            val session = sessionDao.getActiveSession(quizId).toQuizSession(quiz)
            val userAnswers = sessionAnswerDao.getAllAnswersFromSession(quizId).map { it.toSessionAnswer() }
            ResultState.Success(
                SessionResultWithUserAnswers(
                    sessionResult = result,
                    session = session,
                    userAnswers = userAnswers,
                )
            )
        } catch (e : Exception) {
            ResultState.Error(e.message.toString(), e.cause)
        }
    }

    override suspend fun createResult(result: SessionResult) = withContext(Dispatchers.IO) {
        try {
            sessionResultDao.createResult(
                result.toEntity()
            )
        }catch (e : Exception) {
            e.printStackTrace()
        }
    }
}