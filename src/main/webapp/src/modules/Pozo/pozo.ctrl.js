/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
(function(ng)
{
    var mod = ng.module("AppPozo");
  mod.controller('pozoCtrl', ['$scope','pozoSVC','pozoSVC', function ($scope,svc,svcPozo){
    //Acordarse que estas variables no son universales, falta implementar
    //un servicio que las vuelvas de este carácter
     //   $scope.ciudades =  svc.darItinerario();
     
    $scope.currentRecord = {
                id: undefined /*Tipo Long. El valor se asigna en el backend*/,
                nombre: 'Pozo' /*Tipo String*/,
                fechaSalida: '' /*Tipo String*/,
                fechaLlegada: ''
            };
            
            $scope.records = [];
            $scope.alerts = [];
            
            $scope.salida='Cali';
            $scope.mensaje=false;
            
            $scope.today = function () {
                $scope.value = new Date();
            };

            $scope.clear = function () {
                $scope.value = null;
                $scope.mensaje = true;
            };

            $scope.open = function ($event) {
                $event.preventDefault();
                $event.stopPropagation();

                $scope.opened = true;
            };

            //Alertas
            this.closeAlert = function (index) {
                $scope.alerts.splice(index, 1);
            };

            function showMessage(msg, type) {
                var types = ["info", "danger", "warning", "success"];
                if (types.some(function (rc) {
                    return type === rc;
                })) {
                    $scope.alerts.push({type: type, msg: msg});
                }
            }

            this.showError = function (msg) {
                showMessage(msg, "danger");
            };

            var self = this;
            function responseError(response) {
                self.showError(response.data);
            }

            //Variables para el controlador
            this.readOnly = false;
            this.editMode = false;

            this.changeTab = function (tab) {
                $scope.tab = tab;
            };

            //Ejemplo alerta
            showMessage("Bienvenido!, Esto es un ejemplo para mostrar un mensaje de atención", "warning");


            this.createRecord = function () {
                this.editMode = true;
                svc.saveRecord($scope.currentRecord)
               // $scope.currentRecord = {};
               // $scope.$broadcast("post-create", $scope.currentRecord);
            };
            
            this.cambiar = function(){
              this.editMode = true; 
              $scope.mensaje=this.editMode;
              $scope.currentRecord.nombre='Juan';
              
            };

            this.editRecord = function (record) {
                return svc.fetchRecord(record.id).then(function (response) {
                    $scope.currentRecord = response.data;
                    self.editMode = true;
                    $scope.$broadcast("post-edit", $scope.currentRecord);
                    return response;
                }, responseError);
            };

            this.fetchRecords = function () {
                return svc.fetchRecords().then(function (response) {
                   // console.log("Se recupero el "+response.data[0].nombre);
                    $scope.records = response.data;
                    console.log("Se recupero a "+ $scope.records[0].nombre);
                    $scope.currentRecord = {};
                    self.editMode = false;
                    return response;
                }, responseError);
            };
            
            this.saveRecord = function () {
                return svc.saveRecord($scope.currentRecord).then(function () {
                    self.fetchRecords();
                }, responseError);
            };
            this.deleteRecord = function (record) {
                return svc.deleteRecord(record.id).then(function () {
                    self.fetchRecords();
                }, responseError);
            };

            this.fetchRecords();
            
            
 
 
         $scope.ciudad= {};
         $scope.ciudades = [];

         

     


        $scope.newCiudad = function () {





            console.log("Entra a new ciudad " +$scope.ciudad.name + "EL ID es " + $scope.ciudad.idd);

                $scope.ciudad.ID =svcItinerario.darIdItinerarioActual();
                return svc.agregarCiudad($scope.ciudad).then(function () {

                    self.fetchRecords();
                }, "responseError");

                $scope.ciudad={};
            };


//        this.fetchRecords = function () {
//        console.log("Entro a fetchrecords ctrl");
//                return svc.fetchRecords().then(function (response) {
//                    console.log("llamo a svc.fetch "+ response.data);
//                    $scope.ciudades = response.data;
//                    $scope.ciudad = {};
//
//                    return response;
//                }, responseError);
//            };


        $scope.borrar = function()
        {
            console.log("ciudad seleccionada " + $scope.ciudadSeleccionada.name );
            //var temp = $scope.ciudadSeleccionada;
            svc.borrarCity($scope.ciudadSeleccionada.name);
            //var indice = $scope.ciudades.indexOf($scope.ciudadSeleccionada);
            //$scope.ciudades.splice(indice,1);
            self.fetchRecords();
        };

          function responseError(response) {
                self.showError(response.data);
            }

this.fetchRecords();
}]);




})(window.angular);

