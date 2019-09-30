package org.training.poll

import org.approvaltests.Approvals
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import java.io.PrintStream
import java.io.ByteArrayOutputStream



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
    fun `test approval`() {
        // given
        pollService.createPoll(
            0,
            "What is your favorite color?",
            ArrayList(listOf("Blue", "Green", "Red", "Yellow"))
        )

        // when
        pollService.vote(0, 0)
        pollService.vote(0, 0)
        pollService.vote(0, 0)
        pollService.vote(0, 3)
        pollService.vote(0, 2)
        pollService.printResults(0)
        val output = outContent.toString()

        // then
        Approvals.verifyAll("", arrayOf(output))
    }
}