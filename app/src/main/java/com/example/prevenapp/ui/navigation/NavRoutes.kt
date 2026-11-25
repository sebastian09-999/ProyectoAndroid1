package com.example.prevenapp.ui.navigation

sealed class NavRoutes(val route: String, val title: String) {
    object Resumen : NavRoutes("resumen", "Resumen")
    object MisHorarios : NavRoutes("mis_horarios", "Mis Horarios")
    object SalasChat : NavRoutes("salas_chat", "Salas de Chat")
    object PublicarTips : NavRoutes("publicar_tips", "Publicar Tips")
    object ReportesSeguimiento : NavRoutes("reportes_seguimiento", "Reportes y Seguimiento")
    object RecursosEducativos : NavRoutes("recursos_educativos", "Recursos Educativos")
    object MiPerfil : NavRoutes("mi_perfil", "Mi Perfil")
    object CerrarSesion : NavRoutes("cerrar_sesion", "Cerrar Sesión")
}
