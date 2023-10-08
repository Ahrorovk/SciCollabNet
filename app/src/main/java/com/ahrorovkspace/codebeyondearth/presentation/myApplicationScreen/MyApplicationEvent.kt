package com.ahrorovkspace.codebeyondearth.presentation.myApplicationScreen

import com.ahrorovkspace.codebeyondearth.core.states.GetMyApplicationRespState
import com.ahrorovkspace.codebeyondearth.domain.myApplication.model.Result

sealed class MyApplicationEvent {
    data class OnGetMyApplicationChange(val state: GetMyApplicationRespState) : MyApplicationEvent()
    object GetMyApplication : MyApplicationEvent()
    data class OnSelectedResultsChange(val selected: List<Result>) : MyApplicationEvent()
}
