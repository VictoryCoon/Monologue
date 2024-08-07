SI/SM개발자로 지낸지 어언 6년차, 곧 7년차를 맞이하고 있다.

매일매일 격리된 곳에서 남의 것을 만들어주며 느낀 것은 이것 저것 많지만, 그 중 핵심적인 업무역량은 개발자로서의 스킬이 아니란 것만은 가장 정론에 가까운 부분이 아닐까 싶다.

현재는 어떤의미에서 어느 프로젝트에 가담하더라도 1인분 이상 해내는, 한마디로 [일 하는 것엔 이제 아무 문제 없다]는 입장이다.

하지만, 기술자로 살고싶은 나로서는 이 일을 계속 정진하며 굉장히 현타가 오기도한다.

그런 경위에서 스스로 알고있는 개발적인 지식을 짜집기 하여 직접 프로젝트 하나를 구현해보려고 한다.

솔직히 말하면, 누군가가 이것을 보고있다고 생각하면 다소 부끄러운 작업물이 아닐까 싶지만, 언제까지 비슷한 패턴의 업무만 계속해서 하고 지낼 것인가?

어디가서 나같은 사람이랑 일한다는게 창피한 것이 아닌 자랑스러운 부분으로 남았으면 좋겠다는 마음에 조금 더 몸부림쳐 볼 생각이다.

해당 프로젝트는 개발자 인생 중 그나마 내가 남들보다 특별하게 알고있는 서비스에 대하여 혼자서 구현해보는 내용인데, 노하우 자체는 내가 창조한 영역이 아닌지라...

그저 과거의 먼지같은 기억을 조금씩 모아서, 그리고 내가 알고있는 얄팍한 지식을 키워나가며 진행할 예정이다.

현재까지의 절차는 아래와 같으며, 이 내용은 계속 업데이트 될 예정이다.

- Mariadb 생성(Docker)
- Spring Boot Project생성(without web servlet)
- CommandLine 프로세스 구현
- Mybatis 설정
- CommandLine 프로세스로 생성한 Thread로 Transactional CRUD 구현
- TCP Listener 구현
 (* 다중 리스너 구현이 필요하다. 단일 리스너는 PORT의 중첩성을 회피하지 못한다)
- TCP None-Blocking Socket구현
- Concurrent Thread구현
 (* 데이터가 많으면 뻗어버리는 것 까지 확인)
- Redis Sync 구현 / Mybatis Debugging(Multi-resource)
- Zookeeper/Kafka 생성(Docker)
- Kafka Consumer 구현(Server)
- Kafka Procuder 구현(Client)
- Client측 Kafka를 이용하여 메시지 발송
- Kubernetes교육을 위해 VM구축, 그러나 Mac M2(ARM64) 환경에서 제대로된 VM이 없음.
- Spring Batch Job 구현, 기기정보 싱크용 등 활용
