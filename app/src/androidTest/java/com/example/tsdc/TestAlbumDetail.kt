package com.example.tsdc

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestAlbumDetailScreenApi {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun BotonVolverAlbums() {
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
        //Clic en el álbum específico
        composeTestRule.onNodeWithText("Buscando América").performClick()

        //Click en la flecha de volver
        composeTestRule.onNodeWithContentDescription("Volver").performClick()

        //Verificamos que volvimos a la lista
        composeTestRule.onNodeWithText("Álbumes").assertIsDisplayed()
    }
}