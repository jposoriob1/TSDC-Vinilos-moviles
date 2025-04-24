package com.example.tsdc

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.performClick

import org.junit.Rule
import org.junit.Test

class TestAlbumesScreen {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun navegaYSeMuestraListaAlbumes() {
        // Paso 1: Simula clic para abrir la pantalla de álbumes
        composeTestRule
            .onNodeWithText("ALBUMES")
            .performClick()

        // Paso 2: Espera a que se cargue la lista
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule
                .onAllNodesWithTag("lista_albumes")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // Paso 3: Verifica que la lista esté visible
        composeTestRule
            .onNodeWithTag("lista_albumes")
            .assertIsDisplayed()
    }
}
