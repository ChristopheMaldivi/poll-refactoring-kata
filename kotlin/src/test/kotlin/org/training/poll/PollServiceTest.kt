package org.training.poll

import org.approvaltests.Approvals
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.*


class PollServiceTest {

    private val outContent = ByteArrayOutputStream()
    private val errContent = ByteArrayOutputStream()
    private val originalOut = System.out
    private val originalErr = System.err

    private val pollService = PollService()

    @BeforeEach
    fun setup() {
        System.setOut(PrintStream(outContent))
        System.setErr(PrintStream(errContent))
    }

    @AfterEach
    fun restoreStreams() {
        System.setOut(originalOut)
        System.setErr(originalErr)
    }

    @Test
    fun `check PollService goldenMaster`() {
        val pollService = PollService()

        pollService.createPoll(
            "What is your favorite color?",
            ArrayList(listOf("Blue", "Green", "Red", "Yellow"))
        )
        pollService.vote(0, 0)
        pollService.vote(0, 0)
        pollService.vote(0, 0)
        pollService.vote(0, 3)
        pollService.vote(0, 2)
        pollService.printResults(0)

        Approvals.verify(outContent.toString())
    }

    @Test
    fun `check PollService invalid poll exception, goldenMaster`() {
        val pollService = PollService()
        try {
            pollService.vote(1, 0)
            Assertions.fail<String>("InvalidPollId should failed")
        } catch (e: InvalidPollIdException) {
            Assertions.assertThat(e.toString())
                .isEqualTo("org.training.poll.InvalidPollIdException: Id of poll is invalid: 1")
        }
    }


    @Test
    fun `createPoll returns a Poll`() {
        // given
        val pollService = PollService()

        // when
        val poll: Poll = pollService.createPoll("question", ArrayList(listOf("Blue", "Green", "Red", "Yellow")))

        // then
        Assertions.assertThat(poll).isNotNull()
    }


}
