$(document).ready(function()
{
if ($("#alertSuccess").text().trim() == "")
 {
 $("#alertSuccess").hide();
 }
 $("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
// Clear alerts---------------------
 $("#alertSuccess").text("");
 $("#alertSuccess").hide();
 $("#alertError").text("");
 $("#alertError").hide();
// Form validation-------------------
var status = validateItemForm();
if (status != true)
 {
 $("#alertError").text(status);
 $("#alertError").show();
 return;
 }
// If valid------------------------
var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";

$.ajax(
		{
		 url : "AppointmentAPI",
		 type : type,
		 data : $("#formItem").serialize(),
		 dataType : "text",
		 complete : function(response, status)
		 {
		 onItemSaveComplete(response.responseText, status);
		 }
		});
});


function FormToJSON(formArray) {

    var oJSON = {};
    for (var i = 0; i < formArray.length; i++){
        oJSON[formArray[i]['name']] = formArray[i]['value'].trim();
    }

    $.each(oJSON, function(key, value){
        if (value === "" || value === null){
            delete oJSON[key];
        }
    });

    return oJSON;
};


function onItemSaveComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully saved.");
 $("#alertSuccess").show();
 $("#divItemsGrid").html(resultSet.data);
 } else if (resultSet.status.trim() == "error")
 {
 $("#alertError").text(resultSet.data);
 $("#alertError").show();
 }
 } else if (status == "error")
 {
 $("#alertError").text("Error while saving.");
 $("#alertError").show();
 } else
 {
 $("#alertError").text("Unknown error while saving..");
 $("#alertError").show();
 }
 $("#hidItemIDSave").val("");
 $("#formItem")[0].reset();
}

$(document).on("click", ".btnRemove", function(event)
		{
		 $.ajax(
		 {
		 url : "AppointmentAPI",
		 type : "DELETE",
		 data : "appID=" + $(this).data("itemid"),
		 dataType : "text",
		 complete : function(response, status)
		 {
		 onItemDeleteComplete(response.responseText, status);
		 }
		 });
		});


function onItemDeleteComplete(response, status)
{
if (status == "success")
 {
 var resultSet = JSON.parse(response);
 if (resultSet.status.trim() == "success")
 {
 $("#alertSuccess").text("Successfully deleted.");
 $("#alertSuccess").show();
 $("#divItemsGrid").html(resultSet.data);
 } else if (resultSet.status.trim() == "error")
 {
 $("#alertError").text(resultSet.data);
 $("#alertError").show();
 }
 } else if (status == "error")
 {
 $("#alertError").text("Error while deleting.");
 $("#alertError").show();
 } else
 {
 $("#alertError").text("Unknown error while deleting..");
 $("#alertError").show();
 }
}

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
 $("#hidItemIDSave").val($(this).closest("tr").find('#hidItemIDUpdate').val());
 $("#pID").val($(this).closest("tr").find('td:eq(0)').text());
 $("#pName").val($(this).closest("tr").find('td:eq(1)').text());
 $("#add").val($(this).closest("tr").find('td:eq(2)').text());
 $("#dName").val($(this).closest("tr").find('td:eq(3)').text());
 $("#hName").val($(this).closest("tr").find('td:eq(4)').text());
 $("#bDate").val($(this).closest("tr").find('td:eq(5)').text());
 $("#cNo").val($(this).closest("tr").find('td:eq(6)').text());

});
// CLIENTMODEL=========================================================================
function validateItemForm()
{
// CODE
if ($("#pID").val().trim() == "")
 {
 return "Insert payment ID.";
 }
// NAME
if ($("#pName").val().trim() == "")
 {
 return "Insert patient Name.";
 } 
//Address
if ($("#add").val().trim() == "")
 {
 return "Insert address Name.";
 }
//Doctor Name
if ($("#dName").val().trim() == "")
 {
 return "Insert doctor Name.";
 }
//Hospital Name
if ($("#hName").val().trim() == "")
 {
 return "Insert Hospital Name.";
 }//Bookdate
if ($("#bDate").val().trim() == "")
{
return "Insert Bookdate Name.";
}
//PRICE-------------------------------
if ($("#cNo").val().trim() == "")
 {
 return "Insert contact no.";
 }
// is numerical value
var tmpPrice = $("#cNo").val().trim();
if (!$.isNumeric(tmpPrice))
 {
 return "Insert a numerical value for phone number.";
 }
// convert to decimal price
// $("#pAmount").val(parseFloat(tmpPrice).toFixed(2));

return true;
}
