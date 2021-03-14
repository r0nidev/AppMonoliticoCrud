function eliminar(id) {
  console.log("Entered here.");
  console.log(id);
  swal({
    title: "¿Está seguro de eliminar?",
    text: "Una vez eliminado no se podrá restablecer!",
    icon: "warning",
    buttons: true,
    dangerMode: true,
  })
  .then((OK) => {
    if (OK) { // mediante Ajax. Nos pide declarar la URL.
      $.ajax({
        url:"/borrar/"+id,
        //url:"/delete/"+id,
        success: function(res){
          console.log(res);
        },
      });
      swal("Poof! Your imaginary file has been deleted!", {
        icon: "success",
      }).then((ok) => {
          if(ok){
              location.href="/producto/lista";
          }
      });
    /*} else {
      swal("Your imaginary file is safe!");
      console.log('HACKER NIC');
    }*/
   }
  });

}
