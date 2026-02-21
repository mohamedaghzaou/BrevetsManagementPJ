<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

                    </div>
                </div>
            </section>
        </div>
        <!-- END COLORLIB-MAIN -->
    </div>
    <!-- END COLORLIB-PAGE -->

    </section>

    <div class="modal fade" id="deleteConfirmModal" tabindex="-1" role="dialog" aria-labelledby="deleteConfirmModalLabel"
        aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteConfirmModalLabel"><fmt:message key="modal.delete.title" bundle="${i18n}" /></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body" id="deleteConfirmMessage">
                    <fmt:message key="modal.delete.message.default" bundle="${i18n}" />
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary" data-dismiss="modal"><fmt:message key="common.cancel" bundle="${i18n}" /></button>
                    <a id="confirmDeleteBtn" href="#" class="btn btn-danger"><fmt:message key="common.delete" bundle="${i18n}" /></a>
                </div>
            </div>
        </div>
    </div>

    <div id="pageLoadingOverlay" class="page-loading-overlay d-none">
        <div class="text-center">
            <div class="spinner-border text-light" role="status">
                <span class="sr-only"><fmt:message key="common.loading" bundle="${i18n}" /></span>
            </div>
            <div class="mt-2"><fmt:message key="common.loading" bundle="${i18n}" /></div>
        </div>
    </div>

    <script src="js/jquery.min.js"></script>
    <script src="js/popper.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/main.js"></script>
    <script>
        (function() {
            const localeDeleteLabel = '<fmt:message key="modal.delete.item.default" bundle="${i18n}" />';
            const localeDeleteQuestionPrefix = '<fmt:message key="modal.delete.question.prefix" bundle="${i18n}" />';
            const localeDeleteQuestionSuffix = '<fmt:message key="modal.delete.question.suffix" bundle="${i18n}" />';
            const overlay = document.getElementById('pageLoadingOverlay');
            const deleteConfirmBtn = document.getElementById('confirmDeleteBtn');
            const deleteConfirmMessage = document.getElementById('deleteConfirmMessage');

            function showLoadingOverlay() {
                if (overlay) {
                    overlay.classList.remove('d-none');
                }
            }

            document.querySelectorAll("form[data-loading='true']").forEach(function(form) {
                form.addEventListener('submit', function() {
                    showLoadingOverlay();
                });
            });

            document.addEventListener('click', function(event) {
                const loadingLink = event.target.closest("a[data-loading-link='true']");
                if (loadingLink && loadingLink.getAttribute('href') && loadingLink.getAttribute('href') !== '#') {
                    showLoadingOverlay();
                }
            });

            document.addEventListener('click', function(event) {
                const trigger = event.target.closest("[data-confirm-delete='true']");
                if (!trigger) {
                    return;
                }

                event.preventDefault();
                const href = trigger.getAttribute('href');
                const label = trigger.getAttribute('data-delete-label') || localeDeleteLabel;

                if (!href) {
                    return;
                }

                if (deleteConfirmMessage) {
                    deleteConfirmMessage.textContent = localeDeleteQuestionPrefix + " " + label + localeDeleteQuestionSuffix;
                }
                if (deleteConfirmBtn) {
                    deleteConfirmBtn.setAttribute('href', href);
                }

                if (window.jQuery && window.jQuery('#deleteConfirmModal').length) {
                    window.jQuery('#deleteConfirmModal').modal('show');
                } else if (window.confirm(localeDeleteQuestionPrefix + " " + label + localeDeleteQuestionSuffix)) {
                    showLoadingOverlay();
                    window.location.href = href;
                }
            });

            document.querySelectorAll('.js-lang-switch').forEach(function(link) {
                link.addEventListener('click', function(event) {
                    event.preventDefault();
                    const lang = link.getAttribute('data-lang');
                    if (!lang) {
                        return;
                    }
                    const url = new URL(window.location.href);
                    url.searchParams.set('lang', lang);
                    window.location.href = url.toString();
                });
            });

            if (deleteConfirmBtn) {
                deleteConfirmBtn.addEventListener('click', function() {
                    showLoadingOverlay();
                });
            }
        })();
    </script>

</body>

</html>
