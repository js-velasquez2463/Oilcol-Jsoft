/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
(function(ng)
{
    var mod = ng.module("AppHotel");
  mod.controller('hotelCtrl', ['$scope','hotelSVC', function ($scope,svc){
    //Acordarse que estas variables no son universales, falta implementar
    //un servicio que las vuelvas de este carácter
    $scope.hoteles =  svc.darHoteles();
        
        $scope.newHotel = function ()
        {
           svc.agregarHotel({nombre:$scope.nombreHotel, fechaInicio: $scope.fechaInicio, fechaFinal:$scope.fechaFinal});

        console.log($scope.hoteles[0].nombre);
        
        $scope.id="";
        $scope.nombreHotel="";
	$scope.fechaInicio="";
	$scope.fechaFinal="";
        };

        $scope.borrar = function()
        {
            svc.borrarHotel($scope.hotelSeleccionado);
            $scope.hoteles =  svc.darHoteles();
        };

        $scope.guardarTodosLosHoteles = function()
        {
            var hotelActual = svc.darHotelActualId();
            svc.agregarHotelPorId(hotelActual, $scope.hoteles);
        }

}]);

})(window.angular);