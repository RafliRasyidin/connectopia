package com.rasyidin.connectopia.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rasyidin.connectopia.model.component.navigationItems
import com.rasyidin.connectopia.ui.theme.ConnectopiaTheme

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    showNavBar: Boolean = true,
) {
    if (showNavBar) {
        val backgroundColor = MaterialTheme.colorScheme.background
        val primaryColor = MaterialTheme.colorScheme.primary
        val outlineColor = MaterialTheme.colorScheme.outline
        NavigationBar(
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            navigationItems.map { navItem ->
                val selected = currentDestination?.hierarchy?.any { it.route == navItem.screen.route } ?: false
                NavigationBarItem(
                    selected = selected,
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                            LocalAbsoluteTonalElevation.current
                        ),
                        selectedIconColor = primaryColor,
                        unselectedIconColor = outlineColor,
                        selectedTextColor = primaryColor,
                        unselectedTextColor = outlineColor
                    ),
                    onClick =  {
                        if (navItem.icon != -1) {
                            navController.navigate(navItem.screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        }
                    },
                    icon = {
                        if (navItem.icon != -1) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                painter = painterResource(id = if (selected) navItem.iconSelected else navItem.icon),
                                contentDescription = null,
                            )
                        }
                    },
                    label = {
                        if (navItem.icon != -1) {
                            Text(
                                text = stringResource(id = navItem.name),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBottomNavBar(modifier: Modifier = Modifier) {
    ConnectopiaTheme {
        BottomNavBar(navController = rememberNavController())
    }
}