package eu.vctrl4.business.constants

import eu.vctrl4.business.datasource.storage.entities.*

object SessionVars {

	var userSession: Session = Session()
	var fromPushVariables: FromPushVariables = FromPushVariables()
	var isAutoLogin: Boolean = true
}
