(function($) {

	"use strict";

	var burgerMenu = function() {

		$('.js-colorlib-nav-toggle').on('click', function(event){
			event.preventDefault();
			var $this = $(this);

			if ($('body').hasClass('offcanvas')) {
				$this.removeClass('active');
				$('body').removeClass('offcanvas');	
			} else {
				$this.addClass('active');
				$('body').addClass('offcanvas');	
			}
		});
	};
	burgerMenu();

	// Click outside of offcanvass
	var mobileMenuOutsideClick = function() {

		$(document).click(function (e) {
	    var container = $("#colorlib-aside, .js-colorlib-nav-toggle");
	    if (!container.is(e.target) && container.has(e.target).length === 0) {

	    	if ( $('body').hasClass('offcanvas') ) {

    			$('body').removeClass('offcanvas');
    			$('.js-colorlib-nav-toggle').removeClass('active');
			
	    	}
	    	
	    }
		});

		$(window).scroll(function(){
			if ( $('body').hasClass('offcanvas') ) {

    			$('body').removeClass('offcanvas');
    			$('.js-colorlib-nav-toggle').removeClass('active');
			
	    	}
		});

	};
	mobileMenuOutsideClick();

})(jQuery);

(function($) {
	"use strict";

	function isEditableTarget(target) {
		if (!target) {
			return false;
		}
		var tagName = target.tagName ? target.tagName.toLowerCase() : "";
		return tagName === "input" || tagName === "textarea" || tagName === "select" || target.isContentEditable;
	}

	function normalizeSortValue(value, type) {
		var raw = (value || "").trim();
		if (type === "number") {
			var num = parseFloat(raw.replace(",", "."));
			return Number.isNaN(num) ? Number.NEGATIVE_INFINITY : num;
		}
		if (type === "date") {
			var stamp = Date.parse(raw);
			return Number.isNaN(stamp) ? Number.NEGATIVE_INFINITY : stamp;
		}
		return raw.toLocaleLowerCase();
	}

	function compareValues(a, b, direction) {
		if (a === b) {
			return 0;
		}
		return direction === "asc" ? (a > b ? 1 : -1) : (a < b ? 1 : -1);
	}

	function sortTableByColumn(table, headerCell) {
		var tbody = table.querySelector("tbody");
		if (!tbody) {
			return;
		}

		var headers = Array.prototype.slice.call(table.querySelectorAll("thead th[data-sortable='true']"));
		var columnIndex = headerCell.cellIndex;
		var sortType = headerCell.getAttribute("data-sort-type") || "text";
		var currentDir = headerCell.getAttribute("data-sort-dir");
		var nextDir = currentDir === "asc" ? "desc" : "asc";

		headers.forEach(function(h) {
			h.removeAttribute("data-sort-dir");
		});
		headerCell.setAttribute("data-sort-dir", nextDir);

		var rows = Array.prototype.slice.call(tbody.querySelectorAll("tr[data-sort-row='true']"));
		if (rows.length < 2) {
			return;
		}

		rows.sort(function(rowA, rowB) {
			var cellA = rowA.children[columnIndex];
			var cellB = rowB.children[columnIndex];
			var valA = normalizeSortValue(cellA ? cellA.textContent : "", sortType);
			var valB = normalizeSortValue(cellB ? cellB.textContent : "", sortType);
			return compareValues(valA, valB, nextDir);
		});

		rows.forEach(function(row) {
			tbody.appendChild(row);
		});
	}

	function setupTableSorting() {
		document.querySelectorAll(".js-sortable-table").forEach(function(table) {
			table.querySelectorAll("thead th[data-sortable='true']").forEach(function(headerCell) {
				headerCell.addEventListener("click", function() {
					sortTableByColumn(table, headerCell);
				});
			});
		});
	}

	function setupCardSorting() {
		document.querySelectorAll(".js-card-sort").forEach(function(select) {
			select.addEventListener("change", function() {
				var key = select.value;
				var targetSelector = select.getAttribute("data-target");
				if (!key || !targetSelector) {
					return;
				}

				var target = document.querySelector(targetSelector);
				if (!target) {
					return;
				}

				var cards = Array.prototype.slice.call(target.querySelectorAll("[data-card-item='true']"));
				cards.sort(function(cardA, cardB) {
					var attrName = "data-sort-" + key;
					var valA = normalizeSortValue(cardA.getAttribute(attrName), "text");
					var valB = normalizeSortValue(cardB.getAttribute(attrName), "text");
					return compareValues(valA, valB, "asc");
				});

				cards.forEach(function(card) {
					target.appendChild(card);
				});
			});
		});
	}

	function ensureToastContainer() {
		var container = document.getElementById("appToastContainer");
		if (!container) {
			container = document.createElement("div");
			container.id = "appToastContainer";
			container.className = "app-toast-container";
			document.body.appendChild(container);
		}
		return container;
	}

	function showToast(message, type, timeout) {
		if (!message) {
			return;
		}
		var container = ensureToastContainer();
		var toast = document.createElement("div");
		toast.className = "app-toast-item toast-" + (type || "info");
		toast.textContent = message.trim();
		container.appendChild(toast);

		requestAnimationFrame(function() {
			toast.classList.add("show");
		});

		window.setTimeout(function() {
			toast.classList.remove("show");
			window.setTimeout(function() {
				if (toast.parentNode) {
					toast.parentNode.removeChild(toast);
				}
			}, 260);
		}, timeout || 3200);
	}

	function setupAlertToasts() {
		document.querySelectorAll(".alert[data-toast='true']").forEach(function(alert) {
			var message = (alert.textContent || "").trim();
			if (!message) {
				return;
			}
			var type = "info";
			if (alert.classList.contains("alert-success")) {
				type = "success";
			} else if (alert.classList.contains("alert-danger")) {
				type = "danger";
			} else if (alert.classList.contains("alert-warning")) {
				type = "warning";
			}
			showToast(message, type);
			alert.classList.add("d-none");
		});
	}

	function setupKeyboardShortcuts() {
		document.addEventListener("keydown", function(event) {
			if (isEditableTarget(event.target) && event.key !== "Escape") {
				return;
			}

			var key = event.key ? event.key.toLowerCase() : "";
			if (event.altKey && key === "n") {
				event.preventDefault();
				var addButton = document.querySelector(".js-shortcut-add");
				if (addButton) {
					addButton.click();
				}
				return;
			}

			if (event.altKey && key === "f") {
				event.preventDefault();
				var filterTarget = document.querySelector(".js-shortcut-filter")
					|| document.querySelector(".sticky-filter-panel input[type='text']")
					|| document.querySelector(".sticky-filter-panel input[type='search']")
					|| document.querySelector(".sticky-filter-panel select");
				if (filterTarget) {
					filterTarget.focus();
					if (typeof filterTarget.select === "function") {
						filterTarget.select();
					}
				}
				return;
			}

			if (event.altKey && key === "h") {
				event.preventDefault();
				window.location.href = "home";
				return;
			}

			if (!event.altKey && event.key === "?") {
				event.preventDefault();
				var msg = (window.AppUxConfig && window.AppUxConfig.shortcutHelpMessage)
					|| "Shortcuts: Alt+N add, Alt+F filter, Alt+H home.";
				showToast(msg, "info", 4200);
				return;
			}

		});
	}

	window.AppUX = window.AppUX || {};
	window.AppUX.showToast = showToast;

	$(function() {
		setupTableSorting();
		setupCardSorting();
		setupAlertToasts();
		setupKeyboardShortcuts();
	});
})(jQuery);
