(function () {
  "use strict";

  // ---------- Tooltips de Bootstrap (íconos de los botones) ----------
  document.querySelectorAll('[data-bs-toggle="tooltip"]').forEach(function (el) {
    new bootstrap.Tooltip(el);
  });

  // ---------- Confirmaciones con SweetAlert2 (reemplaza window.confirm) ----------
  // Cualquier <form data-confirm="texto"> dispara un modal de SweetAlert2 en vez
  // del confirm() nativo del navegador; si el usuario acepta, se reenvía el form.
  document.querySelectorAll("form[data-confirm]").forEach(function (form) {
    form.addEventListener("submit", function (event) {
      if (form.dataset.swalConfirmado === "true") {
        return; // ya se confirmó, dejar pasar el submit real
      }
      event.preventDefault();

      var mensaje = form.getAttribute("data-confirm") || "¿Confirmas esta acción?";
      var esDestructiva = form.getAttribute("data-confirm-tipo") !== "info";

      Swal.fire({
        title: "¿Estás seguro?",
        text: mensaje,
        icon: esDestructiva ? "warning" : "question",
        showCancelButton: true,
        confirmButtonText: '<i class="bi bi-check2"></i> Sí, continuar',
        cancelButtonText: '<i class="bi bi-x-lg"></i> Cancelar',
        confirmButtonColor: "#0e6e6e",
        cancelButtonColor: "#e0644a",
        reverseButtons: true,
        focusCancel: true
      }).then(function (resultado) {
        if (resultado.isConfirmed) {
          form.dataset.swalConfirmado = "true";
          form.submit();
        }
      });
    });
  });

  // ---------- Validación de formularios (Bootstrap + aviso con SweetAlert2) ----------
  // El resaltado campo a campo lo sigue haciendo Bootstrap (was-validated);
  // SweetAlert2 se usa solo como aviso adicional, visible de inmediato.
  document.querySelectorAll("form.needs-validation").forEach(function (form) {
    form.addEventListener("submit", function (event) {
      if (!form.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
        Swal.fire({
          title: "Revisa el formulario",
          text: "Hay campos obligatorios sin completar o con un formato inválido.",
          icon: "warning",
          confirmButtonText: "Entendido",
          confirmButtonColor: "#0e6e6e"
        });
      }
      form.classList.add("was-validated");
    });
  });

  // Si el formulario vuelve del servidor con errores de validación (Bean Validation
  // vía BindingResult), ya llega con .is-invalid en el HTML: se avisa igual con un toast.
  if (document.querySelector(".is-invalid")) {
    Swal.mixin({
      toast: true,
      position: "top-end",
      showConfirmButton: false,
      timer: 4500,
      timerProgressBar: true
    }).fire({
      icon: "error",
      title: "Corrige los campos marcados en rojo"
    });
  }

  // ---------- Mensajes flash del servidor (éxito / error) como toast ----------
  var flashData = document.getElementById("flash-data");
  if (flashData) {
    var mensaje = flashData.getAttribute("data-mensaje");
    var error = flashData.getAttribute("data-error");
    var Toast = Swal.mixin({
      toast: true,
      position: "top-end",
      showConfirmButton: false,
      timer: 4000,
      timerProgressBar: true
    });
    if (mensaje) {
      Toast.fire({ icon: "success", title: mensaje });
    }
    if (error) {
      Toast.fire({ icon: "error", title: error });
    }
  }

  // ---------- Recalcular subtotal estimado al agregar un tratamiento a una cita ----------
  document.querySelectorAll("[data-costo-base]").forEach(function (select) {
    select.addEventListener("change", actualizarSubtotal);
    var cantidadInput = document.querySelector(select.getAttribute("data-cantidad-target"));
    if (cantidadInput) cantidadInput.addEventListener("input", actualizarSubtotal);

    function actualizarSubtotal() {
      var opcion = select.options[select.selectedIndex];
      var costo = parseFloat(opcion ? opcion.getAttribute("data-costo") : 0) || 0;
      var cantidad = parseFloat(cantidadInput ? cantidadInput.value : 1) || 1;
      var salida = document.querySelector(select.getAttribute("data-output-target"));
      if (salida) {
        salida.textContent = "Subtotal estimado: $" + (costo * cantidad).toLocaleString("es-CO", { minimumFractionDigits: 2 });
      }
    }
  });
})();
