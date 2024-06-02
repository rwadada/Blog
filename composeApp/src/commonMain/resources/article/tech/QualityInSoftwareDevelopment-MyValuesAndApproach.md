# Quality in Software Development: My Values and Approach
## Introduction
In software development, I believe there are two types of "quality." The first is external quality, which involves ensuring the software does not crash and does not perform unintended actions. External quality is crucial as it directly impacts user satisfaction. The second type is internal quality. This refers to maintaining low complexity in development, ensuring that specifications are easily readable from the code, and creating an environment where changes can be made with confidence. Internal quality influences developer motivation and indirectly enhances external quality.

## External Quality
External quality pertains to the aspects of software that users directly experience. Here are the key elements that I consider essential:

1. Reliability: Software should function without unexpected errors or crashes and operate stably.
2. Usability: The interface should be user-friendly and intuitive.
3. Performance: Software should run at an appropriate speed and use resources efficiently.

Achieving high levels of these elements enhances user satisfaction and establishes the software's reliability.

## Internal Quality
Internal quality concerns the aspects of software that developers experience. The key elements include:

1. Code Readability: Specifications should be easily understood from the code, allowing other developers to grasp it quickly and improving maintainability.
2. Reduced Complexity: Complex code is prone to bugs and is challenging to fix. Writing simple and understandable code is crucial.
3. Safe Environment for Changes: Well-maintained tests and CI/CD environments ensure that changes can be made confidently, enabling a swift development cycle.

Improving internal quality boosts developer motivation and, as a result, contributes to the enhancement of external quality.

## Efforts to Improve Quality
There are several ways to enhance quality, but here I will focus on improving quality through refactoring and adopting the latest technologies.

### Enhancing Quality through Refactoring and Adopting Latest Technologies
Improving internal quality can be effectively achieved through refactoring and adopting the latest technologies. However, these efforts come with certain caveats:

1. Refactoring
    - Overview: Organizing code, modularization, and improving naming conventions to enhance code readability and maintainability.
    - Caveats: Conduct refactoring in small steps, ensure thorough testing, and clarify the purpose of refactoring.
2. Adopting Latest Technologies
    - Overview: Performance improvements, enhanced security, and increased development efficiency can be expected.
    - Caveats: Verify applicability, consider the team's skill set, and plan for compatibility and migration processes with existing systems.

### Practical Examples and Lessons Learned
In my current team, I have been tasked with the mission of improving internal quality over the medium to long term. We have been making incremental adjustments and changes towards a defined ultimate goal.

However, we experienced significant failures during this process. By solely focusing on the end goal, we underestimated the confusion among developers during the transition period and the impact of increased implementation methods (resulting in a fragmented architecture). Consequently, complexity increased, bugs proliferated, and new project implementations led to excessive and unnecessary discussions due to inconsistent implementation policies.

From this experience, I learned that the following points are crucial in quality improvement efforts:

1. Achieving Short-Term Effects
    - Incorporate measures that yield short-term improvements to maintain motivation while progressing towards long-term goals. Focusing only on long-term goals can temporarily increase complexity and cause developer confusion.
2. Clearly Defining Scope
    - Clearly define the scope of improvements and proceed within a limited scope to avoid excessive confusion and impact. A phased approach is effective.
    - In line with point 1, setting a broad scope solely based on long-term goals can lead to situations where immediate issues are ignored, leading to an accumulation of technical debt and a negative cycle.
3. Avoiding Increased Complexity when Introducing New Concepts
    - When introducing new technologies or methodologies, ensure that they do not increase complexity and maintain compatibility with existing systems. Introduce changes gradually to minimize impact.
4. Aligning Team Through Documentation and Meetings
    - To ensure the team is aligned and moving in the same direction, establish proper documentation and hold regular meetings to achieve consensus and unify understanding.

## Conclusion
Quality in software development is a critical factor that must be considered from multiple perspectives. By emphasizing both external and internal quality, and implementing appropriate processes and tools, it is possible to deliver high-quality software. Refactoring and adopting the latest technologies can enhance internal quality, which, in turn, contributes to external quality. It is essential to always be mindful of how these improvements affect external quality and to approach quality enhancement with a long-term perspective.

Furthermore, incorporating short-term effective measures, limiting scope, avoiding increased complexity, and aligning the team's understanding are crucial. By keeping these points in mind, I aim to continue striving for sustainable quality improvement in the future.
