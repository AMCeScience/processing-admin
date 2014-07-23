<jsp:include page="/jsp/template/header.jsp" />
<jsp:include page="/jsp/search.jsp" />

<script type="text/javascript">
    $(function() {
        admin_overview();
    });
</script>

<div class="running_projects">
    <div class="accordion">
        <span class="spinner"></span>
    </div>
</div>

<jsp:include page="/jsp/template/footer.jsp" />
<jsp:include page="/jsp/template/dialog.jsp" />