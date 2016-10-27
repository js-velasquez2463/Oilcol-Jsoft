
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
(function(ng)
{
    var mod = ng.module("AppCampo");
    
    mod.controller('campoCtrl', ['$scope','campoSVC','CamposSVC', function ($scope,svc, svcCampo){
        $scope.alerts =[];
        $scope.currentRecord = {
            id: undefined,
            nombre: '',
            fechaInicio: '',
            fechaFinal: '',
            camp:{}
        };
        
        $scope.records = [];
        
        $scope.today = function () {
            $scope.value = new Date();
        };

        $scope.clear = function () {
            $scope.value = null;
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

        // Función showMessage: Recibe el mensaje en String y su tipo con el fin de almacenarlo en el array $scope.alerts.
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

            this.showSuccess = function (msg) {
                showMessage(msg, "success");
            };

            var self = this;
            function responseError(response) {
                self.showError(response.data);
            }

            //Variables para el controlador
            this.readOnly = false;
            this.editMode = false;
            
        
            this.createRecord = function () {
                this.editMode = true;
                $scope.currentRecord = {};
            };

            

            this.editRecord = function (record) {
                return svc.fetchRecord(record.id).then(function (response) {
                    $scope.currentRecord = response.data;
                    self.editMode = true;
                    return response;
                }, responseError);
            };

            

            this.fetchRecords = function () {
                return svc.fetchRecords().then(function (response) {
                    $scope.records = response.data;
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


}]);




})(window.angular);

