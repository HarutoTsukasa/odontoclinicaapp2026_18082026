(function () {
  "use strict";

  // Confirmar antes de enviar cualquier formulario marcado con data-confirm
  document.querySelectorAll("form[data-confirm]").forEach(function (form) {
    form.addEventListener("submit", function (event) {
      var mensaje = form.getAttribute("data-confirm") || "¿Confirmas esta acción?";
      if (!window.confirm(mensaje)) {
        event.preventDefault();
      }
    });
  });

  // Validacion visual de Bootstrap (needs-validation) sin bloquear el envio real,
  // que siempre se valida tambien en el servidor con Bean Validation.
  document.querySelectorAll("form.needs-validation").forEach(function (form) {
    form.addEventListener("submit", function (event) {
      if (!form.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
      }
      form.classList.add("was-validated");
    });
  });

  // Auto-cerrar alertas de mensaje flash despues de unos segundos
  document.querySelectorAll(".alert[data-autohide]").forEach(function (alertEl) {
    setTimeout(function () {
      var alerta = bootstrap.Alert.getOrCreateInstance(alertEl);
      alerta.close();
    }, 4000);
  });

  // Recalcular subtotal estimado en el formulario de "agregar tratamiento" de una cita
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
