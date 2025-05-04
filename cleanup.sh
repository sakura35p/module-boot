#!/bin/bash

MAX_WAIT=300  # 최대 대기 시간(초)
WAIT_INTERVAL=1  # 확인 간격(초)
ELAPSED=1

echo "클러스터 초기화 컨테이너 종료 대기 중..."

while [ $ELAPSED -lt $MAX_WAIT ]; do
  # 컨테이너 상태 확인
  CONTAINER_ID=$(docker ps -a | grep "cluster-init" | awk '{print $1}')

  if [ -z "$CONTAINER_ID" ]; then
    echo "cluster-init 컨테이너를 찾을 수 없습니다."
    exit 0
  fi

  # 컨테이너 상태 확인 (실행 중=running, 종료=exited)
  STATUS=$(docker inspect --format='{{.State.Status}}' $CONTAINER_ID)

  if [ "$STATUS" = "exited" ]; then
    EXIT_CODE=$(docker inspect --format='{{.State.ExitCode}}' $CONTAINER_ID)

    if [ "$EXIT_CODE" = "0" ]; then
      echo "cluster-init 컨테이너가 성공적으로 종료되었습니다(코드: $EXIT_CODE). 컨테이너를 삭제합니다."
      docker rm $CONTAINER_ID
      exit 0
    else
      echo "cluster-init 컨테이너가 오류로 종료되었습니다(코드: $EXIT_CODE). 컨테이너를 유지합니다."
      exit 1
    fi
  fi

  echo "컨테이너가 아직 실행 중입니다. ${WAIT_INTERVAL}초 후 다시 확인합니다. (경과 시간: ${ELAPSED}초)"
  sleep $WAIT_INTERVAL
  ELAPSED=$((ELAPSED + WAIT_INTERVAL))
done

echo "최대 대기 시간(${MAX_WAIT}초)이 초과되었습니다. 강제로 컨테이너를 종료합니다."
docker stop $CONTAINER_ID
docker rm $CONTAINER_ID
exit 1