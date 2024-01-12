window.addEventListener("DOMContentLoaded", function() {
    const toggleActions = document.getElementsByClassName("toggle_action");
    for (const el of toggleActions) {

        const buttonClass = el.dataset.buttonClass;
        const targetClass = el.dataset.targetClass;

        const buttons = el.getElementsByClassName(buttonClass);
        for (const button of buttons) {
            button.addEventListener("click", function() {
                const parentEl = this.parentElement;
                console.log(this);

                const targetEls = parentEl.getElementsByClassName(targetClass);
                for (const ta of targetEls) {
                    const classList = ta.classList;
                    if (classList.contains("hidden")) {
                         classList.remove("hidden");
                    } else {
                        classList.add("hidden");
                    }
                }
            });
        }
    };
});