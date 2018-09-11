<html>
<head>

    <script src="auth/js/keycloak.js"></script>

</head>
<body>
<h2>Hello World!</h2>

<script>

    //keycloak 初始化

    var keycloak = Keycloak();



    //注册监听一些事件的回调



    //登录成功回调

    keycloak.onAuthSuccess = function () {

        alert('Auth Success');

    };

    //登录失败回调

    keycloak.onAuthError = function (errorData) {

        alert("Auth Error: " + JSON.stringify(errorData) );

    };

    //token 刷新成功回调

    keycloak.onAuthRefreshSuccess = function () {

        alert('Auth Refresh Success');

    };

    //token 刷新失败回调

    keycloak.onAuthRefreshError = function () {

        alert('Auth Refresh Error');

    };

    //注销成功回调

    keycloak.onAuthLogout = function () {

        alert('Auth Logout');

    };

    //token过期时回调

    keycloak.onTokenExpired = function () {

        alert('Access token expired.');

    };



    //初始化参数

    var initOptions = {

        responseMode: 'fragment', //可选值：fragment、query

        flow: 'standard',//可选值：standard、implicit、hybrid

        onLoad: 'check-sso' //可选值：check-sso、login-required、或不配置

    };

    keycloak.init(initOptions).success(function(authenticated) {

        alert('Init Success (' + (authenticated ? 'Authenticated' : 'Not Authenticated') + ')');

    }).error(function() {

        alert('Init Error');

    });


</script>
</body>
</html>
