package com.example.prevenapp.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prevenapp.ui.theme.*

@Composable
fun DrawerContent(
    currentRoute: String,
    onNavigate: (NavRoutes) -> Unit,
    onCloseDrawer: () -> Unit
) {
    ModalDrawerSheet(
        drawerContainerColor = Color.White,
        modifier = Modifier.width(300.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp)
        ) {
            // Header del Profesional en el Menú
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(PurplePrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "SM",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "PrevenApp Professional",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = TextPrimary
                    )
                    Text(
                        text = "Dra. Sofía Martínez",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                }
            }

            HorizontalDivider(color = BorderSoft, modifier = Modifier.padding(vertical = 8.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                DrawerItem(
                    title = "Resumen",
                    icon = Icons.Outlined.Dashboard,
                    isSelected = currentRoute == NavRoutes.Resumen.route,
                    onClick = { onNavigate(NavRoutes.Resumen); onCloseDrawer() }
                )
                DrawerItem(
                    title = "Mis Horarios",
                    icon = Icons.Outlined.Schedule,
                    isSelected = currentRoute == NavRoutes.MisHorarios.route,
                    onClick = { onNavigate(NavRoutes.MisHorarios); onCloseDrawer() }
                )
                DrawerItem(
                    title = "Salas de Chat",
                    icon = Icons.Outlined.Chat,
                    isSelected = currentRoute == NavRoutes.SalasChat.route,
                    onClick = { onNavigate(NavRoutes.SalasChat); onCloseDrawer() }
                )
                DrawerItem(
                    title = "Publicar Tips",
                    icon = Icons.Outlined.Lightbulb,
                    isSelected = currentRoute == NavRoutes.PublicarTips.route,
                    onClick = { onNavigate(NavRoutes.PublicarTips); onCloseDrawer() }
                )
                DrawerItem(
                    title = "Reportes y Seguimiento",
                    icon = Icons.Outlined.AssignmentInd,
                    isSelected = currentRoute == NavRoutes.ReportesSeguimiento.route,
                    onClick = { onNavigate(NavRoutes.ReportesSeguimiento); onCloseDrawer() }
                )
                DrawerItem(
                    title = "Recursos Educativos",
                    icon = Icons.Outlined.MenuBook,
                    isSelected = currentRoute == NavRoutes.RecursosEducativos.route,
                    onClick = { onNavigate(NavRoutes.RecursosEducativos); onCloseDrawer() }
                )
                DrawerItem(
                    title = "Mi Perfil",
                    icon = Icons.Outlined.Person,
                    isSelected = currentRoute == NavRoutes.MiPerfil.route,
                    onClick = { onNavigate(NavRoutes.MiPerfil); onCloseDrawer() }
                )
            }

            HorizontalDivider(color = BorderSoft, modifier = Modifier.padding(vertical = 8.dp))

            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                DrawerItem(
                    title = "Cerrar Sesión",
                    icon = Icons.Outlined.ExitToApp,
                    isSelected = false,
                    isDestructive = true,
                    onClick = { onNavigate(NavRoutes.CerrarSesion); onCloseDrawer() }
                )
            }
        }
    }
}

@Composable
private fun DrawerItem(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    isDestructive: Boolean = false,
    onClick: () -> Unit
) {
    val bgColor = if (isSelected) PurpleLightContainer else Color.Transparent
    val contentColor = when {
        isDestructive -> HighRiskRed
        isSelected -> PurplePrimary
        else -> TextPrimary
    }

    NavigationDrawerItem(
        label = {
            Text(
                text = title,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 14.sp
            )
        },
        selected = isSelected,
        onClick = onClick,
        icon = { Icon(icon, contentDescription = title, tint = contentColor) },
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = bgColor,
            unselectedContainerColor = Color.Transparent,
            selectedIconColor = contentColor,
            unselectedIconColor = TextSecondary,
            selectedTextColor = contentColor,
            unselectedTextColor = TextPrimary
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.height(48.dp)
    )
}
