<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<head>
<title>Rate This!</title>
</head>
<body>
	
	<md-toolbar layout="column" class="md-default-theme">
		<!-- ng-click="openMenu()" -->
      <div class="md-toolbar-tools" flex="" layout="column" tabindex="0">

        <div layout="row" flex="">
          <button class="menu-icon" hide-gt-sm="" aria-label="Toggle Menu" style="position: relative; top: -5px;">
            <md-icon icon="img/icons/ic_menu_24px.svg"><object class="md-icon" data="img/icons/ic_menu_24px.svg"></object></md-icon>
          </button>
          <!-- ngIf: menu.currentSection.name -->
          <!-- ngIf: menu.currentPage -->
          <div style="line-height: 28px;" ng-bind="menu.currentPage || 'Beer Buddy'" class="ng-binding">Beer Buddy</div>
          <div flex=""></div>
		
		  <div ng-show="user.isLoggedIn">
		  		
		  	  <a class="md-button md-default-theme hide" ui-sref="profile" style="position: relative; padding-left: 30px; touch-action: pan-y; -webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);" tabindex="0">
	            <md-icon icon="/static/bower_components/material-design-icons/social/svg/design/ic_person_24px.svg" style="
	                height: 28;
	                position: absolute;
	                left: 0px;
	                top: -3px;" class="ng-scope">
	            </md-icon>
	            <span class="ng-scope">Welcome, {{user.name}}</span>
	          </a>
	          <a class="md-button md-default-theme hide" ui-sref="logout" style="position: relative; padding-left: 30px; touch-action: pan-y; -webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);" tabindex="0">
	            <md-icon icon="/static/bower_components/material-design-icons/action/svg/design/ic_settings_power_24px.svg" style="
	                height: 28;
	                position: absolute;
	                left: 0px;
	                top: -3px;" class="ng-scope">
	            </md-icon>
	            <span class="ng-scope">Logout</span>
	          </a>
		  </div>
		  <div ng-show="! user.isLoggedIn">
	          <a class="md-button md-default-theme hide"
	          		ui-sref="login" 
	          		style="position: relative; padding-left: 30px; touch-action: pan-y; -webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);" tabindex="0">
	            <md-icon icon="/static/bower_components/material-design-icons/action/svg/design/ic_settings_power_24px.svg" style="
	                height: 28;
	                position: absolute;
	                left: 0px;
	                top: -3px;" class="ng-scope">
	            </md-icon>
	            <span class="ng-scope">Login</span>
	          </a>
		  </div>
		  
        </div>
      </div>

    </md-toolbar>
		
	<div ui-view></div>
	
</body>
