/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
(function(ng){
    var appCampo = ng.module('AppCampo',["ui.bootstrap"]);
    appCampo.constant("contextoCampo", "http://172.24.42.133:8080/oilcol/getCampos");
})(window.angular);